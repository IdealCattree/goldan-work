package com.ideal.golden.common.foreign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ideal.golden.common.foreign.anno.ForeignCascade;
import com.ideal.golden.common.foreign.anno.ForeignField;
import com.ideal.golden.common.foreign.info.ForeignFieldInfo;
import com.ideal.golden.common.foreign.info.ForeignTableInfo;
import com.ideal.golden.common.utils.Classes;
import com.ideal.golden.common.utils.ResultHelper;
import com.ideal.golden.common.utils.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @作者 Ideal
 * @时间 2022-07-20 10:05
 * @类名 ForeignAspect
 * @类说明
 */
@Component
@Aspect
public class ForeignAspect implements InitializingBean {

    private static final String FOREIGN_SCAN = "classpath*:com/ideal/golden/model/entity/**/*.class";
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private ResourceLoader resourceLoader;

    @Around("execution(* com.ideal.golden.service..*.remove*(..))")
    public Object handleRemove(ProceedingJoinPoint point) throws Throwable {
        Object target = point.getTarget();
        if (!(target instanceof IService)) return point.proceed();

        // 获取Service 对应的model对象
        Class<?> poCls = ((IService<?>) target).getEntityClass();

        // 表格
        ForeignTableInfo table = ForeignTableInfo.get(poCls);
        if (table == null) return point.proceed();

        // 主键
        ForeignFieldInfo mainField = table.getMainField();
        if (mainField == null) return point.proceed();

        // 获取外键约束
        List<ForeignFieldInfo> subFields = mainField.getSubFields();
        if (CollectionUtils.isEmpty(subFields)) return point.proceed();

        // 获取参数值
        Object arg = point.getArgs()[0];
        List<Object> ids;
        if (arg instanceof List) {
            ids = (List<Object>) arg;
        } else {
            ids = new ArrayList<>();
            ids.add(arg);
        }

        for (ForeignFieldInfo subField : subFields) {
            ForeignTableInfo subTable = subField.getTable();
            BaseMapper<Class<?>> mapper = getMapper(subTable.getCls());
            QueryWrapper<Class<?>> wrapper = new QueryWrapper<>();
            wrapper.in(subField.getColumn(), ids);
            if (mainField.getCascade() == ForeignCascade.DEFAULT) { // 默认
                Long count = mapper.selectCount(wrapper);
                if (count == 0) continue;
                ResultHelper.raise(String.format("有%d条【%s】数据相关联，无法删除！", count, subTable.getTable()));
            } else { // 删除关联数据
                mapper.delete(wrapper);
            }
        }
        return point.proceed();
    }

    @Around("execution(* com.ideal.golden.service..*.save*(..)) || execution(* com.ideal.golden.service..*.update*(..)) ")
    public Object handleSaveOrUpdate(ProceedingJoinPoint point) throws Throwable {
        Object target = point.getTarget();
        if (!(target instanceof IService)) return point.proceed();

        // 获取Service 对应的model对象
        Class<?> poCls = ((IService<?>) target).getEntityClass();

        // 表格
        ForeignTableInfo table = ForeignTableInfo.get(poCls);
        if (table == null) return point.proceed();

        // 获取外键约束
        Collection<ForeignFieldInfo> subFields = table.getSubFields().values();
        if (CollectionUtils.isEmpty(subFields)) return point.proceed();

        // 参数
        Object model = point.getArgs()[0];
        if (model.getClass() != poCls) {
            return point.proceed();
        }

        // 遍历外键约束
        for (ForeignFieldInfo subField : subFields) {
            List<ForeignFieldInfo> mainFields = subField.getMainFields();
            if (CollectionUtils.isEmpty(mainFields)) continue;
            // 引用的主键超过1个，无法智能处理，需要手动处理
            if (mainFields.size() > 1) continue;

            Object subValue = subField.getField().get(model);
            // 跳过空值（代表此字段不进行更新）
            if (subValue == null) continue;

            // 唯一的一个主键
            ForeignFieldInfo mainField = mainFields.get(0);
            BaseMapper<Class<?>> mapper = getMapper(mainField.getTable().getCls());
            QueryWrapper<Class<?>> wrapper = new QueryWrapper<>();
            wrapper.eq(mainField.getColumn(), subValue);
            if (mapper.selectCount(wrapper) == 0) {
                ResultHelper.raise(String.format("%s=%s不存在", subField.getColumn(), subValue));
            }
        }
        return point.proceed();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        Resource[] rs = resolver.getResources(FOREIGN_SCAN);
        if (rs.length == 0) {
            ResultHelper.raise("FOREIGN_SCAN配置错误，找不到任何类信息");
        }

        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourceLoader);
        for (Resource r : rs) {
            parseCls(readerFactory.getMetadataReader(r).getClassMetadata().getClassName());
        }
    }


    private BaseMapper<Class<?>> getMapper(Class<?> poCls) {
        String s = Strings.firstLetterLowercase(poCls.getSimpleName());
        s = s.substring(0, s.length() - 2);
        return (BaseMapper<Class<?>>) ctx.getBean(s + "Mapper");
    }

    private void parseCls(String clsName) throws Exception {
        Class<?> subCls = Class.forName(clsName);
        ForeignTableInfo subTable = ForeignTableInfo.get(subCls, true);
        Classes.enumerateFields(subCls, (subField, curCls) -> {
            ForeignField ff = subField.getAnnotation(ForeignField.class);
            parseForeignField(subTable, subField, ff);

            ForeignField.ForeignFields ffs = subField.getAnnotation(ForeignField.ForeignFields.class);
            if (ffs == null) return null;
            for (ForeignField subFf : ffs.value()) {
                parseForeignField(subTable, subField, subFf);
            }
            return null;
        });
    }

    private void parseForeignField(ForeignTableInfo subTable,
                                   Field subField,
                                   ForeignField ff) throws Exception {
        // 跳过没有ForeignField注解的属性
        if (ff == null) return;
        // 主表的类
        Class<?> mainCls = Classes.notObject(ff.mainTable(), ff.value());
        // 说明ForeignField注解的是主键属性（因为缺乏mainCls）
        if (mainCls == null || mainCls.equals(Object.class)) return;

        // 主表中的主键属性
        Field mainField = Classes.getField(mainCls, ff.mainField());
        // 跳过错误（找不到）的主键属性
        if (mainField == null) return;

        // 存储到缓存中
        ForeignTableInfo mainTable = ForeignTableInfo.get(mainCls, true);
        ForeignFieldInfo subFieldInfo = subTable.getSubField(subField);
        ForeignFieldInfo mainFieldInfo = mainTable.getMainField(mainField);

        // 对象之间的关系
        subFieldInfo.addMainField(mainFieldInfo);
        mainFieldInfo.addSubField(subFieldInfo);
    }
}

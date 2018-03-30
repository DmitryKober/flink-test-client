package org.apache.flink.client.model.pojo;

import javassist.tools.reflect.Reflection;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.CompositeType;
import org.apache.flink.api.common.typeutils.TypeComparator;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.typeutils.PojoField;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.runtime.PojoSerializer;
import org.apache.flink.util.ReflectionUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Dmitrii_Kober on 3/29/2018.
 */
public class PojoTypeInfoWrapper<T> extends PojoTypeInfo<T> {

    private final PojoTypeInfo<T> delegate;
    private TypeSerializer<T> serializer;
    private PojoSerializer<T> pojoSerializer;

    public PojoTypeInfoWrapper(PojoTypeInfo<T> delegate) throws IllegalAccessException {
        super(delegate.getTypeClass(), Arrays.asList((PojoField[]) FieldUtils.readField(delegate, "fields", true)));
        this.delegate = delegate;
    }

    @Override
    public TypeSerializer<T> createSerializer(ExecutionConfig executionConfig) {
        if (this.serializer == null) {
            return delegate.createSerializer(executionConfig);
        }
        return serializer;
    }

    public void setSerializer() {
        this.serializer = serializer;
    }

    @Override
    public PojoSerializer<T> createPojoSerializer(ExecutionConfig config) {
        if (this.pojoSerializer == null) {
            return delegate.createPojoSerializer(config);
        }
        return pojoSerializer;
    }

    public void setPojoSerializer(PojoSerializer<T> pojoSerializer) {
        this.pojoSerializer = pojoSerializer;
    }

    @Override
    public Map<String, TypeInformation<?>> getGenericParameters() {
        return delegate.getGenericParameters();
    }

    @Override
    public boolean isSortKeyType() {
        return delegate.isSortKeyType();
    }

    @Override
    public PojoField getPojoFieldAt(int pos) {
        return delegate.getPojoFieldAt(pos);
    }

    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean canEqual(Object obj) {
        return delegate.canEqual(obj);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    @Override
    public Class<T> getTypeClass() {
        return delegate.getTypeClass();
    }

    @Override
    public List<FlatFieldDescriptor> getFlatFields(String fieldExpression) {
        return delegate.getFlatFields(fieldExpression);
    }

    @Override
    public TypeComparator<T> createComparator(int[] logicalKeyFields, boolean[] orders, int logicalFieldOffset, ExecutionConfig config) {
        return delegate.createComparator(logicalKeyFields, orders, logicalFieldOffset, config);
    }

    @Override
    public boolean hasField(String fieldName) {
        return delegate.hasField(fieldName);
    }

    @Override
    public boolean isKeyType() {
        return delegate.isKeyType();
    }

    @Override
    public boolean hasDeterministicFieldOrder() {
        return delegate.hasDeterministicFieldOrder();
    }

    @Override
    public void getFlatFields(String s, int i, List<FlatFieldDescriptor> list) {
        delegate.getFlatFields(s, i, list);
    }

    @Override
    public <X> TypeInformation<X> getTypeAt(String s) {
        return delegate.getTypeAt(s);
    }

    @Override
    public <X> TypeInformation<X> getTypeAt(int i) {
        return delegate.getTypeAt(i);
    }

    @Override
    public String[] getFieldNames() {
        return delegate.getFieldNames();
    }

    @Override
    public int getFieldIndex(String s) {
        return delegate.getFieldIndex(s);
    }

    @Override
    public boolean isBasicType() {
        return delegate.isBasicType();
    }

    @Override
    public boolean isTupleType() {
        return delegate.isTupleType();
    }

    @Override
    public int getArity() {
        return delegate.getArity();
    }

    @Override
    public int getTotalFields() {
        return delegate.getTotalFields();
    }

}

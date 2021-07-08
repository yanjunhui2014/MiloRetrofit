package com.milo.libretrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 7/8/21
 */
public interface CallAdapter<R, T> {

    Type responseType();

    T adapt(Call<R> call);

    abstract class Factory {
        public abstract CallAdapter<?, ?> get(Type returnType, Annotation[] annotations,
                                              Retrofit retrofit);

        protected static Type getParameterUpperBound(int index, ParameterizedType type) {
            return Utils.getParameterUpperBound(index, type);
        }

        protected static Class<?> getRawType(Type type) {
            return Utils.getRawType(type);
        }
    }

}

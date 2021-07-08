package com.milo.libretrofit;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 7/8/21
 */
public interface Converter<F, T> {
    T convert(F value) throws IOException;

    /** Creates {@link Converter} instances based on a type and target usage. */
    abstract class Factory {
        /**
         * Returns a {@link Converter} for converting an HTTP response body to {@code type}, or null if
         * {@code type} cannot be handled by this factory. This is used to create converters for
         * response types such as {@code SimpleResponse} from a {@code Call<SimpleResponse>}
         * declaration.
         */
        @Nullable
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return null;
        }

        /**
         * Returns a {@link Converter} for converting {@code type} to an HTTP request body, or null if
         * {@code type} cannot be handled by this factory. This is used to create converters for types
         * specified by {@link Body @Body}, {@link Part @Part}, and {@link PartMap @PartMap}
         * values.
         */
        public @Nullable Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                                        Annotation[] methodAnnotations, Retrofit retrofit) {
            return null;
        }

        /**
         * Returns a {@link Converter} for converting {@code type} to a {@link String}, or null if
         * {@code type} cannot be handled by this factory. This is used to create converters for types
         * specified by {@link Field @Field}, {@link FieldMap @FieldMap} values,
         * {@link Header @Header}, {@link HeaderMap @HeaderMap}, {@link Path @Path},
         * {@link Query @Query}, and {@link QueryMap @QueryMap} values.
         */
        public @Nullable Converter<?, String> stringConverter(Type type, Annotation[] annotations,
                                                              Retrofit retrofit) {
            return null;
        }

        /**
         * Extract the upper bound of the generic parameter at {@code index} from {@code type}. For
         * example, index 1 of {@code Map<String, ? extends Runnable>} returns {@code Runnable}.
         */
        protected static Type getParameterUpperBound(int index, ParameterizedType type) {
            return Utils.getParameterUpperBound(index, type);
        }

        /**
         * Extract the raw class type from {@code type}. For example, the type representing
         * {@code List<? extends Runnable>} returns {@code List.class}.
         */
        protected static Class<?> getRawType(Type type) {
            return Utils.getRawType(type);
        }
    }
}

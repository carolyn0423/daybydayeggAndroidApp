package com.hamels.daybydayegg.Repository;

import com.google.gson.Gson;
import com.hamels.daybydayegg.Utils.ApiUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class SmileGsonConverterFactory extends Converter.Factory {
    public static final String TAG = SmileGsonConverterFactory.class.getSimpleName();
    private Gson gson;

    public static SmileGsonConverterFactory create() {
        return new SmileGsonConverterFactory();
    }


    private SmileGsonConverterFactory() {
        gson = new Gson();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new ResponseBodyConverter<>(type);
    }

    public class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private Type type;

        public ResponseBodyConverter(Type type) {
            this.type = type;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            try {
                byte[] b = value.bytes();
                String byteString = new String(b);
                String decodeString = ApiUtils.decryption(byteString);
                return new Gson().fromJson(decodeString, type);
            } finally {
                value.close();
            }
        }

    }
}
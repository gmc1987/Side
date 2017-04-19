package com.tssa.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tssa.util.DateWarpUtils.ThreadSafeDateFormat;

/**
 * 提供JSON操作功能
 * <p>
 * 1、json操作类，屏蔽jackson恶心的异常
 * <p>
 * 2、spring restful json支持
 * <p>
 * 常用注解：@JsonIgnore忽略字段；@JsonBackReference关闭回退引用；@JsonIgnoreProperties忽略多个属性
 * 
 * @author whatlly
 */
public class JsonUtils {

	private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

	private static final ObjectMapper JSON_MAPPER = init();

	public static ObjectMapper getObjectMapper() {
		return JSON_MAPPER;
	}

	/**
	 * 生成jackson mapper对象,提供给spring json
	 * 
	 * @return ObjectMapper
	 */
	public static final ObjectMapper init() {
		if (JSON_MAPPER == null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setLocale(Locale.CHINA);
			mapper.setDateFormat(new ThreadSafeDateFormat());
			return mapper;
		}
		return JSON_MAPPER;
	}

	/**
	 * 封装TypeReference类
	 */
	public static class TypeReferenceWarp<T> extends TypeReference<T> {
	}

	/***
	 * 通过JSON输入流，构造对象
	 * 
	 * @param read
	 *            JSON Reader对象
	 * @param clazz
	 *            目标类
	 * @return 目标对象
	 */
	public static <T> T fromJson(Reader read, Class<T> clazz) {
		try {
			return JSON_MAPPER.readValue(read, clazz);
		} catch (JsonParseException e) {
			LOG.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		throw new JsonOperateException("Json transition to clazz fail.");
	}

	/**
	 * 克隆对象
	 * 
	 * @param obj
	 * @return 克隆对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(Object obj) {
		return (T) JsonUtils.fromJsonBytes(JsonUtils.toJsonAsBytes(obj), obj.getClass());
	}

	/***
	 * 通过JSON字符串，构造对象
	 * 
	 * @param read
	 *            JSON字符串
	 * @param clazz
	 *            目标类
	 * @return 目标对象
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return JSON_MAPPER.readValue(json, clazz);
		} catch (JsonParseException e) {
			LOG.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		throw new JsonOperateException("Json transition to clazz fail.");
	}

	/***
	 * 通过JSON字节数组，构造对象
	 * 
	 * @param read
	 *            JSON字符串
	 * @param clazz
	 *            目标类
	 * @return 目标对象
	 */
	public static <T> T fromJsonBytes(byte[] bytes, Class<T> clazz) {
		try {
			return JSON_MAPPER.readValue(bytes, clazz);
		} catch (JsonParseException e) {
			LOG.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		throw new JsonOperateException("Json transition to clazz fail.");
	}

	/**
	 * 集合对象JSON映射，支持自定义结构输出
	 * 
	 * <p>
	 * eg.
	 * <p>
	 * 
	 * String json = JsonUtils.toJson(getMap());
	 * <p>
	 * Map&lt;String, String&gt; map = JsonUtils.fromJson(json, new
	 * TypeReferenceWarp&lt;Map&lt;String, String&gt;&gt;());
	 * <p>
	 * String json = JsonUtils.toJson(getList());
	 * <p>
	 * List&lt;String&gt; list = JsonUtils.fromJson(json, new
	 * TypeReferenceWarp&lt;List&lt;String&gt;&gt;());
	 * 
	 * 
	 * @param json
	 *            输入字符串
	 * @param type
	 *            自定义输出类型
	 * @return 输出自定义对象
	 */
	public static <T> T fromJson(String json, TypeReferenceWarp<T> type) {
		try {
			return JSON_MAPPER.readValue(json, type);
		} catch (JsonParseException e) {
			LOG.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		throw new JsonOperateException("Json transition to clazz fail.");
	}

	/***
	 * 输出JSON字符串
	 * 
	 * @param obj
	 *            对象
	 * @return json字符串
	 */
	public static String toJson(Object obj) {
		try {
			return JSON_MAPPER.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			LOG.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		throw new JsonOperateException("Json transition to clazz fail.");
	}

	/***
	 * 输出JSON字节流
	 * 
	 * @param obj
	 *            对象
	 * @return json字节流
	 */
	public static byte[] toJsonAsBytes(Object obj) {
		try {
			return JSON_MAPPER.writeValueAsBytes(obj);
		} catch (JsonGenerationException e) {
			LOG.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		throw new JsonOperateException("Json transition to clazz fail.");
	}

	/***
	 * 输出JSON到目标文件，UTF-8格式
	 * 
	 * @param file
	 *            JSON目标文件
	 * @param obj
	 *            对象
	 */
	public static void toJsonAsFile(File file, Object obj) {
		try {
			JSON_MAPPER.writeValue(file, obj);
		} catch (JsonGenerationException e) {
			LOG.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/***
	 * 输出JSON数据流
	 * 
	 * @param out
	 *            输出数据流
	 * @param obj
	 *            对象
	 */
	public static void toJsonAsStream(OutputStream out, Object obj) {
		try {
			JSON_MAPPER.writeValue(out, obj);
		} catch (JsonGenerationException e) {
			LOG.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public static class JsonOperateException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2737087299819856404L;

		public JsonOperateException(String message) {
			super(message);
		}

		public JsonOperateException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}

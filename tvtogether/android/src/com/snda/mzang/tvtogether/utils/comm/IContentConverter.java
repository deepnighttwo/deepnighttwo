package com.snda.mzang.tvtogether.utils.comm;


public interface IContentConverter<T> {
	public T convert(byte[] content) throws Exception;
}

package com.douzone.mysite.exception;

public class GalleryRepositoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public GalleryRepositoryException(String message) {
		super(message);
	}
	
	public GalleryRepositoryException() {
		super();
	}

}

/**
 * 
 */
package com.tssa.common.mode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author gmc
 *
 */
public class TssaBaseException extends Exception {

	private static final Logger LOG = LoggerFactory.getLogger(TssaBaseException.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -2977098400392383021L;
	
	public TssaBaseException(String msg){
		LOG.error(msg);
	}

}

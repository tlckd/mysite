package com.douzone.mysite.web.mvc.board;

import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("writeform".equals(actionName)) {
			action = new WriteFromAction();
		}else if("write".equals(actionName)){
			action= new WriteAction();
		}else if("viewform".equals(actionName)){
			action= new ViewFromAction();
		}else {
		return new IndexAction();
		}
		return action;
		
	}

}

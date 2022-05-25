package com.douzone.mysite.web.mvc.guestbook;


import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class GuestbookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action= null;

		if("listform".equals(actionName)) {
			action=new IndexAction();
		}else if("add".equals(actionName)){
			action = new AddAction();
		}else if("deleteform".equals(actionName)){
			action = new DeleteFromAction();
		}else if("delete".equals(actionName)){
			action = new DeleteAction();
		}else {
			action = new IndexAction();
		}
		return action;
	}

}

package com.poscoict.mysite.mvc.guestbook;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;

public class GuestbookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if ("deleteform".equals(actionName)) {
			action = new DeleteformAction();
		} else if ("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if ("add".equals(actionName)) {
			action = new AddAction();
		} else {
			action = new IndexAction();
		}
		
		return action;
	}

}

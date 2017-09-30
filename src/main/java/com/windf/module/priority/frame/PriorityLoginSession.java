package com.windf.module.priority.frame;

import java.util.List;

import com.windf.core.frame.SessionContext;
import com.windf.core.spring.SpringUtil;
import com.windf.module.priority.Constant;
import com.windf.module.priority.entity.Priority;
import com.windf.module.priority.service.PriorityService;
import com.windf.module.sso.modle.AbstractLoginObserver;
import com.windf.module.user.UserSession;

/**
 * 权限
 * @author chenyafeng
 *
 */
public class PriorityLoginSession extends AbstractLoginObserver {

	@Override
	public void login() {
		PriorityService priorityService = (PriorityService) SpringUtil.getBean("priorityService");
		List<Priority> prioritys = priorityService.getPrioritiesByRoleId(UserSession.getCurrentUser().getRole().getId());
		SessionContext.set(Constant.SESSION_PRIORITY, prioritys);
	}

	@Override
	public void logout() {
		
	}

}

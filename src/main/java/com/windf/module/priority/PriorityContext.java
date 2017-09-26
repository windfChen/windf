package com.windf.module.priority;

import java.util.List;

import com.windf.core.frame.session.SessionContext;
import com.windf.core.util.CollectionUtil;
import com.windf.core.util.StringUtil;
import com.windf.module.priority.entity.Priority;

public class PriorityContext {
		
		/**
		 * 验证是否有对应权限
		 * @param path
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static boolean verify(String path) {
			List<Priority> prioritys = (List<Priority>) SessionContext.get(Constant.SESSION_PRIORITY);
			if (StringUtil.isNotEmpty(path) && CollectionUtil.isNotEmpty(prioritys)) {
				for (Priority priority : prioritys) {
					if (priority.getUrl().contains(path)) {
						return true;
					}
				}
			}
			return false;
		}
}

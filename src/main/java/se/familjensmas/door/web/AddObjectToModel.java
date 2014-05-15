package se.familjensmas.door.web;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * @author jorgen.smas@entercash.com
 */
public class AddObjectToModel implements WebRequestInterceptor {

	private String attributeName;
	private Object attributeValue;

	@Override
	public void preHandle(WebRequest request) throws Exception {
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		if (model != null) {
			model.addAttribute(getAttributeName(), getAttributeValue());
		}
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Object getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(Object attributeValue) {
		this.attributeValue = attributeValue;
	}
}

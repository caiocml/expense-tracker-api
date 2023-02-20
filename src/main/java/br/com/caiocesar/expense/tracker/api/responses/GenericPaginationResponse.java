package br.com.caiocesar.expense.tracker.api.responses;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;
/**
 * Generic Response for less data transfer, this class returns a cleaner pagination.
 * @author Caio Cesar
 *
 * @param <T>
 */
public class GenericPaginationResponse<T> {
	
	protected Long totalElements;
	protected Integer elementsSize;
	protected Integer page;
	protected Integer totalPages;
	protected List<T> content;
	
	/**
	 * Create a object with a generic content list.
	 * @param page
	 */
	public GenericPaginationResponse(Page<T> page) {
		this(page, false);
	}
	/**
	 * Creates with the content as null if the {@code true} is setted on nullContent
	 * so the list of content can be setted on the class implementation to return a field with 
	 * another name for a better response management.
	 * @param page
	 * @param nullContent
	 */
	protected GenericPaginationResponse(Page<T> page, boolean nullContent) {
		Objects.requireNonNull(page, "Page can't be null");
		
		this.totalElements = page.getTotalElements();
		this.elementsSize = page.getNumberOfElements();
		this.page = page.getNumber() + 1;
		this.totalPages = page.getTotalPages();
		if (! nullContent ) this.content = page.getContent();
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getElementsSize() {
		return elementsSize;
	}

	public void setElementsSize(Integer elementsSize) {
		this.elementsSize = elementsSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
	
	

}

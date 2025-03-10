package br.com.caiocesar.expense.tracker.api.dto;

public class PaymentTypeDTO implements GenericDto{

	private Integer id;
	private String description;
	private Integer lastCardNumber;
	private String cardBrandName;
	private String cardBankName;
	private Long accountNumber;
	private Integer bankNumber;
	private Integer agencyNumber;
	private Integer userId;
	private Integer expirationDay;
	private Integer daysToCloseInvoice;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getLastCardNumber() {
		return lastCardNumber;
	}
	public void setLastCardNumber(Integer lastCardNumber) {
		this.lastCardNumber = lastCardNumber;
	}
	public String getCardBrandName() {
		return cardBrandName;
	}
	public void setCardBrandName(String cardBrandName) {
		this.cardBrandName = cardBrandName;
	}
	public String getCardBankName() {
		return cardBankName;
	}
	public void setCardBankName(String cardBankName) {
		this.cardBankName = cardBankName;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Integer getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(Integer bankNumber) {
		this.bankNumber = bankNumber;
	}
	public Integer getAgencyNumber() {
		return agencyNumber;
	}
	public void setAgencyNumber(Integer agencyNumber) {
		this.agencyNumber = agencyNumber;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}


    public Integer getExpirationDay() {
        return expirationDay;
    }

    public void setExpirationDay(Integer expirationDay) {
        this.expirationDay = expirationDay;
    }

    public Integer getDaysToCloseInvoice() {
        return daysToCloseInvoice;
    }

    public void setDaysToCloseInvoice(Integer daysToCloseInvoice) {
        this.daysToCloseInvoice = daysToCloseInvoice;
    }
}

package com.example.demo.utils;

import java.math.BigDecimal;

public class AemetDTO {

	private String idema;
	private String fint;
	private BigDecimal ts; //t suelo
	private BigDecimal tamin;
	private BigDecimal tamax;
	private BigDecimal prec; //precipitaciones
	private BigDecimal hr; //humedad
	
	public AemetDTO() {}

	public String getIdema() {
		return idema;
	}

	public void setIdema(String idema) {
		this.idema = idema;
	}

	public String getFint() {
		return fint;
	}

	public void setFint(String fint) {
		this.fint = fint;
	}

	public BigDecimal getTs() {
		return ts;
	}

	public void setTs(BigDecimal ts) {
		this.ts = ts;
	}

	public BigDecimal getTamin() {
		return tamin;
	}

	public void setTamin(BigDecimal tamin) {
		this.tamin = tamin;
	}

	public BigDecimal getTamax() {
		return tamax;
	}

	public void setTamax(BigDecimal tamax) {
		this.tamax = tamax;
	}

	public BigDecimal getPrec() {
		return prec;
	}

	public void setPrec(BigDecimal prec) {
		this.prec = prec;
	}

	public BigDecimal getHr() {
		return hr;
	}

	public void setHr(BigDecimal hr) {
		this.hr = hr;
	}
	
	
}

package com.example.demo.utils;

import java.math.BigDecimal;

public class AemetDTOWeek2 {

	private String fecha; //"2020-10-06"
	private BigDecimal prec;
	private BigDecimal tmax;
	private BigDecimal tmin;
	private BigDecimal tmed;
	private BigDecimal sol; //horas de sol
	
	public AemetDTOWeek2(AemetDTOWeek response) {
		this.fecha = response.getFecha();
		this.prec = convert(response.getPrec());
		this.tmax = convert(response.getTmax());
		this.tmin = convert(response.getTmin());
		this.tmed = convert(response.getTmed());
		this.sol = convert(response.getSol());
	}

	private BigDecimal convert(String string) {
		return new BigDecimal(string.replaceAll(",","."));
	}

	public String getFecha() {
		
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getPrec() {
		return prec;
	}

	public void setPrec(BigDecimal prec) {
		this.prec = prec;
	}

	public BigDecimal getTmax() {
		return tmax;
	}

	public void setTmax(BigDecimal tmax) {
		this.tmax = tmax;
	}

	public BigDecimal getTmin() {
		return tmin;
	}

	public void setTmin(BigDecimal tmin) {
		this.tmin = tmin;
	}

	public BigDecimal getTmed() {
		return tmed;
	}

	public void setTmed(BigDecimal tmed) {
		this.tmed = tmed;
	}

	public BigDecimal getSol() {
		return sol;
	}

	public void setSol(BigDecimal sol) {
		this.sol = sol;
	}
	
}

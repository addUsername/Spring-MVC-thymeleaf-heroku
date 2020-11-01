package com.example.demo.utils;

public class AemetDTOWeek {

	private String fecha; //"2020-10-06"
	private String prec;
	private String tmax;
	private String tmin;
	private String tmed;
	private String sol; //horas de sol
	
	public AemetDTOWeek(){}

	public AemetDTOWeek(String fecha, String prec, String tmax, String tmin, String tmed,
			String sol) {
		super();
		this.fecha = fecha;
		this.prec = prec;
		this.tmax = tmax;
		this.tmin = tmin;
		this.tmed = tmed;
		this.sol = sol;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getPrec() {
		return prec;
	}

	public void setPrec(String prec) {
		this.prec = prec;
	}

	public String getTmax() {
		return tmax;
	}

	public void setTmax(String tmax) {
		this.tmax = tmax;
	}

	public String getTmin() {
		return tmin;
	}

	public void setTmin(String tmin) {
		this.tmin = tmin;
	}

	public String getTmed() {
		return tmed;
	}

	public void setTmed(String tmed) {
		this.tmed = tmed;
	}

	public String getSol() {
		return sol;
	}

	public void setSol(String sol) {
		this.sol = sol;
	}
	
	
}

package com.example.demo.utils;

public class AemetServiceDTO {

	private String descripcion;
	private int estado;
	private String datos;
	private String metadatos;
	
	public AemetServiceDTO() {};
	
	public AemetServiceDTO(String descripcion, int estado, String datos, String metadatos) {
		super();
		this.descripcion = descripcion;
		this.estado = estado;
		this.datos = datos;
		this.metadatos = metadatos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}

	public String getMetadatos() {
		return metadatos;
	}

	public void setMetadatos(String metadatos) {
		this.metadatos = metadatos;
	}	
}

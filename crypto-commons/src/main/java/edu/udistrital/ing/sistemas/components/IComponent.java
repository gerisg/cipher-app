package edu.udistrital.ing.sistemas.components;

public interface IComponent {

	public enum Type {
		generator, cipher, signer;
	}

	public String getName();

	public Type getType();

}

package com.catho.jobsearch.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Job implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String title;
	private final String description;
	private final BigDecimal salario;
	private final Collection<String> cidade;
	private final Collection<String> cidadeFormated;

	public Job() {
		this.title = null;
		this.description = null;
		this.salario = null;
		this.cidade = null;
		this.cidadeFormated = null;
	}

	public Job(@JsonProperty("title") String title,
			@JsonProperty("description") String description,
			@JsonProperty("salario") BigDecimal salario,
			@JsonProperty("cidade") Collection<String> cidade,
			@JsonProperty("cidadeFormated") Collection<String> cidadeFormated) {
		this.title = title;
		this.description = description;
		this.salario = salario;
		this.cidade = cidade;
		this.cidadeFormated = cidadeFormated;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public Collection<String> getCidade() {
		return cidade;
	}

	public Collection<String> getCidadeFormated() {
		return cidadeFormated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((cidadeFormated == null) ? 0 : cidadeFormated.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((salario == null) ? 0 : salario.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (cidadeFormated == null) {
			if (other.cidadeFormated != null)
				return false;
		} else if (!cidadeFormated.equals(other.cidadeFormated))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (salario == null) {
			if (other.salario != null)
				return false;
		} else if (!salario.equals(other.salario))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Job[title=")
				.append(title)
				.append(", description=")
				.append(description)
				.append(", salario=")
				.append(salario)
				.append(", cidade=")
				.append(cidade)
				.append(", cidadeFormated=")
				.append(cidadeFormated)
				.append("]");
		return sb.toString();
	}

}

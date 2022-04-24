package br.com.zup.edu.nossosistemadereservadesala.sala;

import static br.com.zup.edu.nossosistemadereservadesala.sala.StatusOcupacao.LIVRE;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Entity
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusOcupacao status = LIVRE;

    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime atualizadoEm = LocalDateTime.now();


    public Sala(String nome) {
        this.nome = nome;
    }

    @Deprecated
    /**
     * @deprecated construtor para uso
     */
    public Sala() {
    }

    public Long getId() {
        return id;
    }
    
    private boolean isLivre() {
		return this.status == StatusOcupacao.LIVRE;
	}
    
	public void reservar() {
		if(!isLivre()) {
    		throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Sala não está livre para reservar");
    	}
		
		this.atualizadoEm = LocalDateTime.now();
		this.status = StatusOcupacao.OCUPADO;
	}
}

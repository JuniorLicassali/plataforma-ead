package com.plataforma.plataforma_ead.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.api.dto.PagamentoDTO;
import com.plataforma.plataforma_ead.domain.model.Pagamento;

@Component
public class PagamentoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PagamentoDTO toDTO(Pagamento pagamento) {
		return modelMapper.map(pagamento, PagamentoDTO.class);
	}
	
	
	public List<PagamentoDTO> toCollectionDTO(Collection<Pagamento> pagamentos) {
		return pagamentos.stream()
				.map(pagamento -> toDTO(pagamento))
				.collect(Collectors.toList());
	}
	
}

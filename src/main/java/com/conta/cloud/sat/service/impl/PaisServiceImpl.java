package com.conta.cloud.sat.service.impl;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.exception.CatalogExceptionConstants;
import com.conta.cloud.sat.exception.ErrorDetail;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.dto.PaisDTO;
import com.conta.cloud.sat.service.PaisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collection;                                                                                                                        
import java.util.Collections;
import java.util.List;
import org.springframework.util.ResourceUtils;
import java.io.File;

@Slf4j
@Service
public class PaisServiceImpl implements PaisService {


    private final ObjectMapper mapper;

    public PaisServiceImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Collection<PaisDTO> findPaises() throws CatalogException {
        try {
            File file = ResourceUtils.getFile("classpath:catalogs/country-list.json");
            TypeReference<List<PaisDTO>> typeReference = new TypeReference<List<PaisDTO>>(){};
            List<PaisDTO> paises = mapper.readValue(file, typeReference);
            return paises;    
        } catch (Exception e) {
            log.error("Unable to read file", e);
            CatalogException ce = new CatalogException(ErrorCode.INTERNAL, //
					Collections.singletonList(new ErrorDetail(CatalogExceptionConstants.INTERNAL_CODE, INTERNAL_ERROR_MESSAGE)));
			throw ce;
        }
    }

}
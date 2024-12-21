package com.example.happyDream.Util;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.Entity.ChargerEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class Converter {
    public static <E, D> List<D> EntityListToDtoList(List<E> entityList, Function<E, D> toDtoFunction) {
        // Stream을 사용하여 Entity -> Dto 변환 후 리스트로 반환
        if (entityList.isEmpty()) {
            log.warn("Entity list가 비어있음");
            return new ArrayList<>();
        }
        return entityList.stream()
                .map(toDtoFunction) // 각 엔티티를 DTO로 변환
                .collect(Collectors.toList());  // 변환된 결과를 리스트로 수집
    }
    public static <D, E> List<E> DtoListToEntityList(List<D> dtoList, Function<D, E> toEntityFunction) {
        // Stream을 사용하여 Entity -> Dto 변환 후 리스트로 반환
        if (dtoList.isEmpty()) {
            log.warn("DTO list가 비어있음");
            return new ArrayList<>();
        }
        return dtoList.stream()
                .map(toEntityFunction) // 각 엔티티를 Entity로 변환
                .collect(Collectors.toList());  // 변환된 결과를 리스트로 수집
    }
}

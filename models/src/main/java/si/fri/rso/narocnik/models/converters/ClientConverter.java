package si.fri.rso.narocnik.models.converters;

import si.fri.rso.narocnik.lib.Client;
import si.fri.rso.narocnik.models.entities.ClientEntity;

public class ClientConverter {

    public static Client toDto(ClientEntity entity) {

        Client dto = new Client();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setVip(entity.getVip());
        dto.setLocation(entity.getLocation());

        return dto;
    }

    public static ClientEntity toEntity(Client dto) {

        ClientEntity entity = new ClientEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setVip(dto.getVip());
        entity.setLocation(dto.getLocation());

        return entity;
    }
}
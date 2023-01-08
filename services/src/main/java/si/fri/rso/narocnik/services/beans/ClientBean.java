package si.fri.rso.narocnik.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.narocnik.lib.Client;
import si.fri.rso.narocnik.models.converters.ClientConverter;
import si.fri.rso.narocnik.models.entities.ClientEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;


@RequestScoped
public class ClientBean {

    @Inject
    private EntityManager em;

    public List<Client> getClients() {
        TypedQuery<ClientEntity> query = em.createNamedQuery("ClientEntity.getAll", ClientEntity.class);

        List<ClientEntity> resultList = query.getResultList();

        return resultList.stream().map(ClientConverter::toDto).collect(Collectors.toList());
    }

    public List<Client> getClientsFilter(UriInfo uriInfo) {


        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();

        return JPAUtils.queryEntities(em, ClientEntity.class, queryParameters).stream()
                .map(ClientConverter::toDto).collect(Collectors.toList());
    }

    public Client getClient(Integer id) {

        ClientEntity clientEntity = em.find(ClientEntity.class, id);

        if (clientEntity == null) {
            throw new NotFoundException();
        }

        Client client = ClientConverter.toDto(clientEntity);

        return client;
    }

    public Client createClient(Client client) {

        ClientEntity clientEntity = ClientConverter.toEntity(client);

        try {
            beginTx();
            em.persist(clientEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (clientEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return ClientConverter.toDto(clientEntity);
    }

    public Client putClient(Integer id, Client client) {

        ClientEntity person = em.find(ClientEntity.class, id);

        if (person == null) {
            return null;
        }

        ClientEntity updatedClientEntity = ClientConverter.toEntity(client);

        try {
            beginTx();
            updatedClientEntity.setId(person.getId());
            updatedClientEntity = em.merge(updatedClientEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return ClientConverter.toDto(updatedClientEntity);
    }

    public boolean deleteClient(Integer id) {

        ClientEntity client = em.find(ClientEntity.class, id);

        if (client != null) {
            try {
                beginTx();
                em.remove(client);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

}

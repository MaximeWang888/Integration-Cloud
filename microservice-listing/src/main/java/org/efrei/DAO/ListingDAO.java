package org.efrei.DAO;

import org.efrei.Entity.Listing;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ListingDAO implements IListingDAO {

    public List<Listing> findByLocationAndTypeAndBudget(String location, String type, int budget) {
        // Implémentation de l'accès aux données
        return List.of();
    }

    public List<Listing> findByTypeAndLocationAndMinPrice(String type, String location, int minPrice) {
        // Implémentation de l'accès aux données
        return List.of();
    }
    public Listing save(Listing listing) {
        // Implémentation de l'accès aux données pour enregistrer une nouvelle annonce
        return null;
    }

    public Listing findById(String listingId) {
        // Implémentation de l'accès aux données pour récupérer une annonce par son ID
        return null;
    }

    public void update(Listing listing) {
        // Implémentation de l'accès aux données pour mettre à jour une annonce
    }

    public void delete(String listingId) {
        // Implémentation de l'accès aux données pour supprimer une annonce
    }
}
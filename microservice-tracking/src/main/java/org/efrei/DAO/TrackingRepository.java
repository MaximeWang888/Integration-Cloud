package org.efrei.DAO;

import org.efrei.Entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    List<Tracking> findByBookingId(Long bookingId);
}

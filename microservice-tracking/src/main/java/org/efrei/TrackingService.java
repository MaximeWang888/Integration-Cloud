package org.efrei;

import org.efrei.DAO.TrackingRepository;
import org.efrei.entity.Tracking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingService {
    @Autowired
    private TrackingRepository trackingRepository;

    public List<Tracking> getTrackingByBookingId(Long bookingId) {
        return trackingRepository.findByBookingId(bookingId);
    }

    public Tracking addTracking(Tracking tracking) {
        return trackingRepository.save(tracking);
    }
}

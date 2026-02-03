package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.AppointmentSearchRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Appointment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AppointmentSearchSpec {

    public Specification<Appointment> build(AppointmentSearchRequest req) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (req.getDoctorId() != null) {
                predicates.add(
                        cb.equal(root.get("doctor").get("id"), req.getDoctorId())
                );
            }

            if (req.getStatus() != null) {
                predicates.add(
                        cb.equal(root.get("status"), req.getStatus())
                );
            }

            if (req.getFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("appointmentDateTime"),
                                req.getFrom()
                        )
                );
            }

            if (req.getTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("appointmentDateTime"),
                                req.getTo()
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}


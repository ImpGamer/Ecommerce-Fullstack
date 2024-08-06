package com.backend.ecommerce.application.controller;

import com.backend.ecommerce.application.service.PayPalService;
import com.backend.ecommerce.domain.model.DataPayment;
import com.backend.ecommerce.domain.model.PayPalResponse;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@Slf4j
@RequestMapping(value = "/payments")
@CrossOrigin(origins = "http://localhost:3200")
public class PayPalController {

    @Autowired
    private PayPalService payPalService;
    private final String SUCCESS_URL = "http://localhost:8085/payments/success";
    private final String CANCELED_URL = "http://localhost:8085/payments/canceled";

    @PostMapping("/doing")
    ResponseEntity<PayPalResponse> createPayment(@RequestBody DataPayment dataPayment) {
        try {
            Payment payment = payPalService.createPayment(
                    Double.valueOf(dataPayment.getAmount()),
                    dataPayment.getCurrency(),
                    dataPayment.getMethod(),
                    "SALE",
                    dataPayment.getDescription(),
                    CANCELED_URL,
                    SUCCESS_URL
            );
            //Una vez creado el pago, paypal nos devolvera algunos links que tendra el objeto "Payment"
            for(Links link: payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok(new PayPalResponse(link.getHref()));
                }
            }

        }catch (PayPalRESTException ex) {
            log.error("Error al crear el pago "+ex.getMessage());
        }

        return ResponseEntity.ok(new PayPalResponse("http://localhost:3200"));
    }
    @GetMapping("/success")
    public RedirectView successPayment(@RequestParam("paymentId")String paymentId,@RequestParam("PayerID")String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId,payerId);
            if(payment.getState().equals("approved")) {
                return new RedirectView("http://localhost:3200/payment/success");
            }

        }catch (PayPalRESTException ex) {
            log.error("Error al ejecutar el pago "+ex.getMessage());
        }
        return new RedirectView("http://localhost:3200");
    }
    @GetMapping("/canceled")
    public RedirectView cancelPayment() {
        return new RedirectView("http://localhost:3200");
    }
}

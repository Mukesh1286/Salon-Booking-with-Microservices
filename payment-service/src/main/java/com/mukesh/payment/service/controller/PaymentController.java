package com.mukesh.payment.service.controller;

import com.mukesh.payment.service.domain.PaymentMethod;
import com.mukesh.payment.service.model.PaymentOrder;
import com.mukesh.payment.service.payload.dto.BookingDTO;
import com.mukesh.payment.service.payload.dto.UserDTO;
import com.mukesh.payment.service.payload.response.PaymentLinkResponse;
import com.mukesh.payment.service.service.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO booking,
            @RequestParam PaymentMethod paymentMethod
            ) throws StripeException, RazorpayException {
        UserDTO user = new UserDTO();
        user.setFulName("Mukesh Prajapati");
        user.setEmail("prajapatimukesh020196@gmail.com");
        user.setId(1L);

        PaymentLinkResponse res = paymentService.createOrder(user,booking,paymentMethod);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
            @PathVariable Long paymentorderId

    ) throws Exception {


        PaymentOrder res = paymentService.getPaymentOrderById(paymentorderId);
        return ResponseEntity.ok(res);
    }


    @PatchMapping("/{proceed}")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId

    ) throws Exception {


        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);
        Boolean res = paymentService.proceedPayment(paymentOrder,
                paymentId,
                paymentLinkId);
        return ResponseEntity.ok(res);
    }



}

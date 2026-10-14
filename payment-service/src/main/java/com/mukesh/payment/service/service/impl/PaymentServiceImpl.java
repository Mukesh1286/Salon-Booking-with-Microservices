package com.mukesh.payment.service.service.impl;

import com.mukesh.payment.service.domain.PaymentMethod;
import com.mukesh.payment.service.domain.PaymentOrderStatus;
import com.mukesh.payment.service.model.PaymentOrder;
import com.mukesh.payment.service.payload.dto.BookingDTO;
import com.mukesh.payment.service.payload.dto.UserDTO;
import com.mukesh.payment.service.payload.response.PaymentLinkResponse;
import com.mukesh.payment.service.repository.PaymentOrderRepository;
import com.mukesh.payment.service.service.PaymentService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;


    @Override
    public PaymentLinkResponse createOrder(UserDTO user,
                                           BookingDTO booking,
                                           PaymentMethod paymentMethod) throws RazorpayException, StripeException {

        Long amount = (long) booking.getTotalPrice();

        PaymentOrder order = new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());
        PaymentOrder saveOrder = paymentOrderRepository.save(order);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            PaymentLink payment= createRazorpayPaymentLink(user,
                    saveOrder.getAmount(),
                    saveOrder.getId());

            String paymentUrl = payment.get("short_url");
            String paymentUrlId = payment.get("id");

            paymentLinkResponse.setPayment_link_url(paymentUrl);
            paymentLinkResponse.setGetPayment_link_id(paymentUrlId);

            paymentOrderRepository.save(saveOrder);



        }else {

            String paymentUrl = createStripePaymentLink(user,
                    saveOrder.getAmount(),
                    saveOrder.getId());
            paymentLinkResponse.setPayment_link_url(paymentUrl);


        }

        return paymentLinkResponse;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {

        PaymentOrder paymentOrder = paymentOrderRepository.findById(id).orElse(null);
        if(paymentOrder==null){
            throw new Exception("payment order not found");
        }

        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return paymentOrderRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId) throws RazorpayException {


        Long amounts = amount*100;


            RazorpayClient razorpay = new RazorpayClient(razorpayApiKey,razorpayApiSecret);

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","INR");

        JSONObject customer= new JSONObject();
        customer.put("name",user.getFulName());
        customer.put("email",user.getEmail());

        paymentLinkRequest.put("customer",customer);

        JSONObject notify= new JSONObject();
        notify.put("email",true);


        paymentLinkRequest.put("notify",notify);

        paymentLinkRequest.put("reminder_enable", true);

        paymentLinkRequest.put("callback_url","http://localhost:3000/payment-success/"+orderId);

       paymentLinkRequest.put("callback_method","get");

       return razorpay.paymentLink.create(paymentLinkRequest);

    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey=stripeSecretKey;

        SessionCreateParams params= SessionCreateParams.builder()
                .addAllowedPaymentMethodType(SessionCreateParams.AllowedPaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/"+orderId)
                .setCancelUrl("http:/localhost:3000/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData.builder().setName("salon Appointment booking").build()
                                ).build()
                        ).build()
                ).build();

        Session session=Session.create(params);

        return session.getUrl();
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder,
                                  String paymentId,
                                  String paymentLinkId) throws RazorpayException {

        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
                RazorpayClient razorpay=new RazorpayClient(razorpayApiKey,razorpayApiSecret);

                Payment payment = razorpay.payments.fetch(paymentId);
                Integer amount= payment.get("amount");
                String status = payment.get("status");

                if(status.equals("capture")){

                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    paymentOrderRepository.save(paymentOrder);
                    return  true;
                }
           return false;

            } else {
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOrder);
                return  true;
            }

        }

        return false;
    }
}

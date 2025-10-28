package com.payment.Payment_Service.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StkCallbackResponse {

    @JsonProperty("Body")
    private Body body;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Body{
        @JsonProperty("stkCallback")
      private StkCallback stkCallback;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StkCallback{
        @JsonProperty("MerchantRequestID")
        private String merchantRequestId;
        @JsonProperty("CheckoutRequestID")
        private String checkoutRequestId;

        @JsonProperty("ResultCode")
        private int responseCode;
        @JsonProperty("ResultDesc")
        private String responseDescription;

        @JsonProperty("CallbackMetadata")
        private CallbackMetadata callbackMetadata;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CallbackMetadata{

        @JsonProperty("Item")
        List<Item> item;

    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Item{

        @JsonProperty("Name")
        private String name;
        @JsonProperty("Value")
        private Object value;

    }
}

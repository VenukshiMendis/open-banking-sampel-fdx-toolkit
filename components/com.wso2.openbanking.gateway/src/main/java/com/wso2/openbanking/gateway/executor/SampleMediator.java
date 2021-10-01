package com.wso2.openbanking.gateway.executor;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

/**
 * Sample mediator implementation
 */
public class SampleMediator extends AbstractMediator {

    @Override
    public boolean mediate(MessageContext messageContext) {
        // Add mediation logic
        return true;
    }
}

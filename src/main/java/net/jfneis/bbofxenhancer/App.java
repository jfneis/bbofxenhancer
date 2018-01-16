package net.jfneis.bbofxenhancer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.webcohesion.ofx4j.domain.data.MessageSetType;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.domain.data.creditcard.CreditCardResponseMessageSet;
import com.webcohesion.ofx4j.io.AggregateMarshaller;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import com.webcohesion.ofx4j.io.OFXParseException;
import com.webcohesion.ofx4j.io.v2.OFXV2Writer;

public class App 
{
	//TODO transformar em classe a parte

    public static void main( String[] args ) throws FileNotFoundException, IOException, OFXParseException
    {
    	if (args.length != 1)
    		throw new IllegalArgumentException("Número de parâmetros inválido.");
    	
    	String input = args[0];
    	String output = args[0] + "_enhanced.ofx";
    	
    	try (InputStream inputStream = new FileInputStream(input); OutputStream outputStream = new FileOutputStream(output))
    	{
    		// deserializa o OFX
        	AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<ResponseEnvelope>(ResponseEnvelope.class);
        	ResponseEnvelope response = unmarshaller.unmarshal(inputStream);

        	// faz as transformações necessárias nas transações
        	CreditCardResponseMessageSet cc = (CreditCardResponseMessageSet)response.getMessageSet(MessageSetType.creditcard);
        	List<Transaction> transactions = cc.getStatementResponse().getMessage().getTransactionList().getTransactions();
        	CompraParceladaEnhancer compraParceladaEnhancer = new CompraParceladaEnhancer();
        	transactions.stream()
        		.forEach(compraParceladaEnhancer::enhance);
        	
        	// serializa novamente o OFX
        	AggregateMarshaller marshaller = new AggregateMarshaller();
        	OFXV2Writer writer = new OFXV2Writer(outputStream); 
        	marshaller.marshal(response, writer);
        	writer.flush();
    	}
    }
}

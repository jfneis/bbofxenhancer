package net.jfneis.bbofxenhancer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.webcohesion.ofx4j.domain.data.common.Transaction;

public class CompraParceladaEnhancer
{
	private final String COMPRA_PARCELADA_REGEX = ".*PARC (\\d{2})\\/(\\d{2}).*";
	private final Integer GRUPO_NR_PARCELA = 1;
	// private final Integer GRUPO_QT_PARCELA = 2;
	
	public Transaction enhance(Transaction tx)
	{
		Pattern pattern = Pattern.compile(COMPRA_PARCELADA_REGEX);
		Matcher matcher = pattern.matcher(tx.getMemo());

		if (matcher.matches())
		{
			Integer nrParc = Integer.parseInt(matcher.group(GRUPO_NR_PARCELA));
			tx.setDatePosted(this.adicionarMes(tx.getDatePosted(), nrParc-1));
		}
		
		return tx;
	}
	
	private Date adicionarMes(Date date, Integer meses)
	{
		LocalDate localDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		return java.sql.Date.valueOf(localDate.plusMonths(meses));
	}
}

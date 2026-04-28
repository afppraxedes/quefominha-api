package br.com.gva.quefominha.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.MaskFormatter;

public class UtilitarioSistema {

	public static String getDataAtualCompleta(String cidade) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
		LocalDate hoje = LocalDate.now();
		
		return cidade.concat(", ").concat(dtf.format(hoje)).concat(".");
	}

	public static String getCEP(String cep) {
		try {
			MaskFormatter mask = new MaskFormatter("#####-###");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(cep);
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String getTelefone(String telefone) {
		try {
			String pattern = null;

			if (telefone.length() == 10) {
				pattern = "(##) ####-####"; // fixo com ddd
			} else if (telefone.length() == 11) {
				pattern = "(##) #####-####"; // celular com ddd
//				pattern = "(##) #-####-####"; // celular com ddd
			}

			MaskFormatter mask = new MaskFormatter(pattern);
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(telefone);
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String getDataSimples(Date date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR"));
		LocalDate hoje = date.toLocalDate();
		
		return dtf.format(hoje);
	}
	
	public static String getDataSimples(LocalDate date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR"));
		LocalDate hoje = date;
		
		return dtf.format(hoje);
	}

	public static String getCPF(String cpf) {
		try {
			MaskFormatter mask = new MaskFormatter("###.###.###-##");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(cpf);
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String getRG(String cpf) {
		try {
			MaskFormatter mask = new MaskFormatter("##.###.###-#");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(cpf);
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String getValorMonetario(BigDecimal valor, boolean usaSimboloMoeda) {

		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		DecimalFormatSymbols dfs = ((DecimalFormat) format).getDecimalFormatSymbols();

		if (!usaSimboloMoeda) {
			dfs.setCurrencySymbol("");
		}

		((DecimalFormat) format).setDecimalFormatSymbols(dfs);

		return format.format(valor);
	}
	
	public static String formatarDecimalMoedaReal(BigDecimal decimal) {
        if (decimal != null && decimal.scale() < 2) {
            decimal = decimal.setScale(2, RoundingMode.HALF_UP);
        }

        return formatarDecimal(decimal, "R$ ###,##0.");
    }
	
	public static String formatarDecimal(BigDecimal decimal) {
        String formato = "###,##0.";
        return formatarDecimal(decimal, formato);
    }
	
	public static String formatarDecimalMoedaRealSemPrefixo(BigDecimal decimal) {
        if (decimal != null && decimal.scale() < 2) {
            decimal = decimal.setScale(2, RoundingMode.HALF_UP);
        }

        return formatarDecimal(decimal, "###,##0.");
    }
	
	public static String formatarDecimal(BigDecimal decimal, String formato) {
        if (decimal == null) {
            return null;
        } else {
            Integer decimais = decimal.scale();
            formato = formato.split("\\.").length > 1 ? formato : StringUtils.rightPad(formato, formato.length() + (decimais == 0 ? 2 : decimais), '0');
            DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
            DecimalFormat formatoDecimal = new DecimalFormat(formato, symbols);
            return formatoDecimal.format(decimal.doubleValue());
        }
    }
	
	public static String encodePassword(String password) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

}





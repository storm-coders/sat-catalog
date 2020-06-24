package exception;

import java.util.HashSet;
import java.util.Set;

import com.conta.cloud.sat.service.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CatalogException extends Exception{
	private ErrorCode code;
	private Set<ErrorDetail> errors = new HashSet<ErrorDetail>();
	/**
	 * 
	 */
	private static final long serialVersionUID = -2808432347078432353L;
	
	
	
	public void addError(ErrorDetail detail) {
		this.errors.add(detail);
	}



	public CatalogException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}	
}

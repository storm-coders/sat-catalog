package exception;

import java.util.ArrayList;
import java.util.List;
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
	private List<ErrorDetail> errors = new ArrayList<ErrorDetail>();
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

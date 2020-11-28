package com.conta.cloud.sat.domain;

public enum IncluirPropiedad {
    OPT("opcional"),
    YES("sí"),
    NO("no");

    final String option;

    IncluirPropiedad(String option) {
        this.option = option.toLowerCase();
    }

    public String getOption() {
        return option;
    }

    public static IncluirPropiedad fromString(String option){
	if(option == null)
	    return IncluirPropiedad.OPT;	
        String lowerOption = option.toLowerCase();
        switch(lowerOption) {
            case "sí":
            case "si":
                return YES;
            case "no":
                return NO;
            case "opcional":
	    case "Opcional":
                return OPT;        
        }
	throw new IllegalArgumentException("Invalid parameter");
    }

}

package escola.ti.controleparental.model.util;

import lombok.Getter;

public class Password {
    @Getter
    private String password;

    private Short[] part = new Short[6];

    public Password(){
        setPassword();
    }

    private void setPassword(){
        String resposta = "";

        for(int i = 0; i < 6; i++){
            this.part[i] = (short)Math.floor(Math.random()*(9-0+1)+0); // insere um numero aleatorio de 0 a 9 em um dos 6 vetores
            resposta += this.part[i]; // transforma o vetor em string
        }

        this.password = resposta; // passa para a senha
    }

}

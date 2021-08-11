
class ContatoController {
    carregarLista(lista) {
        let corpoTabela = document.getElementById("corpoTabela");
        corpoTabela.innerHTML = "";
        for (let i = 0; i < lista.length; i++) {
            let linha = corpoTabela.insertRow();


            let idCell = linha.insertCell();
            idCell.innerHTML = lista[i].id;
            

            let nomeCompletoCell = linha.insertCell();
            nomeCompletoCell.innerHTML = lista[i].nomeCompleto;
            
            let telefoneCell = linha.insertCell();
            telefoneCell.innerHTML = lista[i].telefone;


            let apagarCell = linha.insertCell();
            apagarCell.innerHTML = `<button onclick="contatoController.apagar(${lista[i].id})">Apagar</button>`;

            let editarCell = linha.insertCell();
            editarCell.innerHTML = `<button 
                         onclick="contatoController.editarItem(${lista[i].id})">Editar</button>`;
            
            
        }
    }

    editarItem(id) {
        fetch(`api/contatos/${id}`, {
            method: "GET"
        }).then((resposta) => {
            if (resposta.ok) {
                resposta.json().then(
                        (item) => {
                    this.atribuirItem(item);
            
                }
                );
            }else{
                 
            }
        });
    }
    atribuirItem(item) {
        document.getElementById("id").value = item.id;
        document.getElementById("nomeCompleto").value = item.nomeCompleto;
        document.getElementById("telefone").value = item.telefone;
   
        
    }

    apagar(id) {
        fetch(`api/contatos/${id}`, {
            method: "DELETE"
        }
        ).then((resposta) => {
            if (resposta.ok) {
                alert('Contato Deletado!');
                this.listar();
            } else {
                console.log("Erro ao apagar");
            }

        });

    }
    
    pesquisarNomeCompleto() {
        let pesquisaNomeCompleto = document.getElementById("pesquisarNomeCompleto").value;
        fetch(`/api/contatos/pesquisa/nomeCompleto/?contem=${pesquisaNomeCompleto}`, {method: "GET"})
        
                .then((resultado) => {
                    if (resultado.ok) {
                        // retorno ok
                        resultado.json().then(
                                (lista) => {
                            this.carregarLista(lista);
                            console.log(lista);
                        }
                        );

                    } else {
                        
                        // tratar o erro 
                        console.log("Erro na excecução");


                    }

                }

                );

    }
    
    pesquisarTelefone() {
        let pesquisaTelefone = document.getElementById("pesquisarTelefone").value;
        fetch(`/api/contatos/pesquisa/telefone/?contem=${pesquisaTelefone}`, {method: "GET"})
        
                .then((resultado) => {
                    if (resultado.ok) {
                        // retorno ok
                        resultado.json().then(
                                (lista) => {
                            this.carregarLista(lista);
                            console.log(lista);
                        }
                        );

                    } else {
                        
                        // tratar o erro 
                        console.log("Erro na excecução");


                    }

                }

                );

    }


  

    listar() {
        fetch("api/contatos/", {method: "GET"})
                .then((resultado) => {
                    if (resultado.ok) {
                        // retorno ok
                        resultado.json().then(
                                (lista) => {
                            this.carregarLista(lista);
                            console.log(lista);
                            
                        }
                        );

                    } else {
                        // tratar o erro 
                        console.log("Erro na excecução");


                    }

                }

                );

    }
    

    

    confirmar() {
        
        let id = document.getElementById("id").value;
        let nomeCompleto = document.getElementById("nomeCompleto").value;
        let telefone = document.getElementById("telefone").value;
        
        
        
         let item = {
            nomeCompleto: nomeCompleto,
            telefone: telefone
        };
        
   
        
        if(nomeCompleto == '') {
        alert('O campo nome é obrigatório');
        }
        
        if(telefone == '') {
        alert('O campo telefone é obrigatório');
        }
      
        
        if (id == "") {
            this.inserir(item);
 
        } else {
            this.editar(id, item);
        
        }

     
    } 

    editar(id, item) {
        fetch(`api/contatos/${id}`, {
            method: "PUT",
            
            headers: new Headers({
                'Content-Type': 'application/json'
                
            }),
            
            body: JSON.stringify(item)
        }).then((resultado) => {
            if (resultado.ok) {
                alert('Contato Editado!');
                this.limpar();
                this.listar();

            }
        });
    }   

    limpar(limpar) {
        document.getElementById("nomeCompleto").value = "";
        document.getElementById("telefone").value = "";

    }
    
   

    inserir(item) {
        fetch("api/contatos/", {
            method: "POST",
            
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(item)
            
        }).then((resultado) => {
            if (resultado.ok) {
            alert('Contato Cadastrado!');
                this.listar();
            } else {
                console.log("Erro na execução");
            }

        });
    } 
}



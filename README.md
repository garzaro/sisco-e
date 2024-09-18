# Sistema de consulta escolar - sisco-e

**Caso de Uso: Gerenciamento de Usuários e Escolas**

**Ator Principal:** Usuário

**Pré-condições:** O usuário deve estar autenticado no sistema.

### Fluxo Principal:

1. **Criar Usuário:**
    - O usuário acessa a funcionalidade de criação de usuário.
    - O sistema solicita as informações necessárias (nome, email, CPF, etc.).
    - O usuário preenche os dados e confirma a criação.
    - O sistema valida as informações e cria o usuário.

2. **Cadastrar Escola:**
    - O usuário acessa a funcionalidade de cadastro de escola.
    - O sistema solicita as informações da escola (nome, endereço, código, etc.).
    - O usuário preenche os dados e confirma o cadastro.
    - O sistema valida as informações e cadastra a escola.

3. **Atualizar Escola:**
    - O usuário acessa a funcionalidade de atualização de escola.
    - O sistema solicita o código da escola a ser atualizada.
    - O usuário fornece o código e as novas informações.
    - O sistema valida e atualiza os dados da escola.

4. **Deletar Escola:**
    - O usuário acessa a funcionalidade de deleção de escola.
    - O sistema solicita o código da escola a ser deletada.
    - O usuário fornece o código e confirma a deleção.
    - O sistema valida e deleta a escola.

5. **Buscar Escola por Nome e Código:**
    - O usuário acessa a funcionalidade de busca de escola.
    - O sistema solicita o nome ou código da escola.
    - O usuário fornece o nome ou código.
    - O sistema realiza a busca e exibe os resultados correspondentes.

6. **Buscar Usuário por Email e CPF:**
    - O usuário acessa a funcionalidade de busca de usuário.
    - O sistema solicita o email ou CPF do usuário.
    - O usuário fornece o email ou CPF.
    - O sistema realiza a busca e exibe os resultados correspondentes.

**Pós-condições:** As operações de criação, atualização, deleção e busca são realizadas com sucesso, e o sistema reflete as mudanças conforme solicitado pelo usuário.


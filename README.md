# EquipCatalogApp

Data de Desenvolvimento/ultima modificação : 12/01/2023

Nome do desenvolvedor : Victor Vagner Perez

Nome do projeto : Equip Catalog App

# Desafio Pratico (Dev.And.Jun) e Projeto Pessoal

 # Sobre o aplicativo 
 É um aplicativo de cadastro e catalogo de equipamentos.
 
 # Características 
 Este aplicativo representa Splash Activity, Main Activity, New Equipment Activity e tres visualizações de fragmentos de recursos 
 1. All equips Fragment,
 2. Equipment Details Fragment,
 3. Critical Equips Fragment,

 # SPLASH ACTIVITY
 A animação de abertura é mostrada sempre que o aplicativo é aberto\
![splash screen_300x600](https://github.com/VictorPerez3/Teste-Pratico---Victor-Perez/blob/main/Splash.jpg)

 
 # MAIN ACTIVITY
 Esta atividade é usada para manter todas as nossas visualizações de fragmento e também para ocultar e mostrar a vista de navegação inferior e também para executar notificações Android para o usuário deste aplicativo \
 ![main3_727x600 (3)](https://github.com/VictorPerez3/Teste-Pratico---Victor-Perez/blob/main/Tela_Inicial_All_Equipments.jpg)


 # NEW EQUIP ACTIVITY
 Esta atividade permite que o usuário adicione ou atualize os detalhes do equipamento dentro do banco de dados Os detalhes do equipamento incluem: 
 1 Imagem do equipamento
 2 Marca + Nome do equipamento
 3 Codigo do equipamento
 4 Tipo do equipamento
 5 Local 1 do equipamento
 6 Local 2 do equipamento
 7 Notas do equipamento \
![new equip_300x600](https://github.com/VictorPerez3/Teste-Pratico---Victor-Perez/blob/main/Add_Equipments.jpg)



 # 1. All equips Fragment
 Este fragmento permite que o usuário filtre os equipamentos adicionados usando um menu de filtro de local e nome/codigo e também adicionar equipamentos usando o ícone de menu adicionar / mais que abre a ATIVIDADE ADICIONAR EQUIPAMENTO.
 Este Fragmento também apresenta ao usuário uma lista de equipamentos adicionados ao nosso banco de dados de dispositivos locais
 cada lista é preenchida com um menu pop-up que permite ao usuário editar ou excluir o equipamento.
 Este Fragmento usa gráficos de navegação para navegar até EQUIPMENT DETAILS FRAGMENT para exibir detalhes do equipamento
 adicionado ao banco de dados\
 ![all equips_300x600](https://github.com/VictorPerez3/Teste-Pratico---Victor-Perez/blob/main/All_Equipments_Search_Name.jpg)
 


 # 2. Equipment Details Fragment
 Este fragmentos exibe os detalhes do equipamento para o usuário, também permite que o usuário compartilhe o equipamento usando o menu do ícone de compartilhamento.
 O usuário pode optar por deixar critico ou não critico o equipamento usando o ícone de coração
 se o usuário ativar o critico, o prato será exibido para o FRAGMENTO DE EQUIPAMENTOS CRITICOS.
 Este fragmento usa a ferramenta (palette android) para colorir o layout\
 ![equipment details_300x600](https://github.com/VictorPerez3/Teste-Pratico---Victor-Perez/blob/main/Equipments_Details.jpg)


 # 3. Critical Equips Fragment
 Este Fragmento exibe uma lista de todos os equipamentos criticos que, quando clicados, navega de volta para o FRAGMENTO DE DETALHES DO EQUIPAMENTO\
 ![critical equips_300x600](https://github.com/VictorPerez3/Teste-Pratico---Victor-Perez/blob/main/Critical_Equipments.jpg)
 
 
 # Android Teste automatizado (JUnit4 - Android Studio)
 1. Na aba superior, clique RUN, RUN, ANDROID TEST.
 2. fun testeListaCorreta: Se a função equipsLocal1() retorna os dados da lista e verifica se eles estão corretos.
 3. fun testeMecanismoDeBuscaEmListaPorNome(): Realiza uma filtragem por nome na lista criada e verifica se o funcionamento é como esperado.
 4. fun testeMecanismoDeBuscaEmListaPorCodigo(): Realiza uma filtragem por codigo na lista criada e verifica se o funcionamento é como esperado
 


 # Bibliotecas utilizadas 
 * SDP - A scalable size unit library\
 implementation 'com.intuit.sdp:sdp-android:1.0.6'

 * Dexter runtime permissions library\
 implementation 'com.karumi:dexter:6.2.3'

 * Image loading library\
 For more detail visit the link: https://github.com/bumptech/glide
 implementation 'com.github.bumptech.glide:glide:4.13.0'\
 annotationProcessor 'com.github.bumptech.glide:compiler:4.13.0'

 * Room components\
 Reference Link: https://developer.android.com/training/data-storage/room
 def room_version = "2.4.2"\
 implementation "androidx.room:room-ktx:$room_version"\
 To use Kotlin annotation processing tool (kapt)\
 kapt "androidx.room:room-compiler:$room_version"\
 implementation 'androidx.palette:palette-ktx:1.0.0'

 * Retrofit\
 implementation 'com.squareup.retrofit2:retrofit:2.9.0'\
 implementation "com.squareup.retrofit2:converter-gson:2.9.0"\
 implementation 'com.squareup.retrofit2:adapter-rxjava3:2.9.0'

 * RxJava\
 implementation 'io.reactivex.rxjava3:rxandroid:3.0.0'\
 implementation 'io.reactivex.rxjava3:rxjava:3.0.0'
 
 * JUnit4 test\
 testImplementation 'junit:junit:4.+'
 androidTestImplementation 'androidx.test.ext:junit:1.1.3'

 # Outras Ferramentas / Tecnologias
 * Android Jetpack suite
 * ViewBinding and Animation(Splash Screen)
 * MVVM (Model View ViewModel)
 * Permissions
 * Glide
 * ROOM Database and CRUD Operations
 * Coroutines, LiveData, Lifecycles and ViewModels
 * Navigation Component, Navigation Graph, Safe Args
 * Android Palette
 * JUnit4 test
 * Retrofit and RxJava


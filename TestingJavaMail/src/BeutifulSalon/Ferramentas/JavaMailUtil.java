package BeutifulSalon.Ferramentas;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler; //imports para imagem
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Melissa
 */
public class JavaMailUtil {

    public static void sendMail( String recepient ) throws Exception {

        System.out.println( "Preparando para enviar email" );
        Properties properties = new Properties();

        properties.put( "mail.smtp.auth", "true" );
        properties.put( "mail.smtp.starttls.enable", "true" );
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com"); //linha adicionada para antivirus não dar problema
        properties.put( "mail.smtp.host", "smtp.gmail.com" );
        properties.put( "mail.smtp.port", "587" );

        String myAccountEmail = "dev1belosalao@gmail.com";
        String password = "testeProjBS";

        Session session = Session.getInstance( properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication( myAccountEmail, password );
            }
        } );

        Message message = prepareMessage( session, myAccountEmail, recepient );

        Transport.send( message );
        System.out.println( "Mensagem enviada com sucesso!" );

    }

    private static Message prepareMessage(
            Session session,
            String myAccountEmail,
            String recepient ) {

        try {

            Message message = new MimeMessage( session );
            message.setFrom( new InternetAddress( myAccountEmail ) );
            message.setRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress( recepient ) );

            message.setSubject( "Email com imagem" );
            //message.setText( "Acho que pode dar certo o teste hahahha" );
            
            // Criando a parte que vai tratar a imagem
            MimeMultipart multipart = new MimeMultipart( "related" );
            
            // Corpo da mensagem
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<h1>Oi, tudo bem?"
                    + "<p> Notamos que tem 3 meses que você não nos visita, o que acha de marcar um horário com a gente?"
                    + "<p> Ligue para (11) 4002-8922 e agende um horário."
                    + "<p> Estamos aguardando sua visita!</h1>"
                    + "<img src='cid:image' />";
            messageBodyPart.setContent(htmlText, "text/html");
            // Add
            multipart.addBodyPart( messageBodyPart );
            
            // Pegando a imagem
            messageBodyPart = new MimeBodyPart();
            DataSource img = new FileDataSource( "./src/beautifulSalon/img/cartao-visita.jpg" );
            messageBodyPart.setDataHandler( new DataHandler(img) );
            messageBodyPart.setHeader( "Content-ID", "<image>" );
            // Add a imagem
            multipart.addBodyPart( messageBodyPart );
            
            // Juntando tudo
            message.setContent( multipart );
            
            return message;

        } catch ( Exception ex ) {
            Logger.getLogger( JavaMailUtil.class.getName() ).log( Level.SEVERE, null, ex );
        }

        return null;

    }
}

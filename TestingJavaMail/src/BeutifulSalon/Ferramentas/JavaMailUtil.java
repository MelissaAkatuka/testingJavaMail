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

            message.setSubject( "Meu primeiro email" );
            message.setText( "Oi, \n Olha meu email!" );

            return message;

        } catch ( Exception ex ) {
            Logger.getLogger( JavaMailUtil.class.getName() ).log( Level.SEVERE, null, ex );
        }

        return null;

    }
}

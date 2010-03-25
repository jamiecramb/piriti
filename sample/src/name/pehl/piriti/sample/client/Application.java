package name.pehl.piriti.sample.client;

import java.util.List;

import name.pehl.piriti.sample.client.event.BooksReadEvent;
import name.pehl.piriti.sample.client.event.BooksReadHandler;
import name.pehl.piriti.sample.client.event.EventBus;
import name.pehl.piriti.sample.client.model.Book;
import name.pehl.piriti.sample.client.model.BookModel;
import name.pehl.piriti.sample.client.rest.BooksClient;
import name.pehl.piriti.sample.client.util.TimeInterval;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author $Author$
 * @version $Date$ $Revision: 292
 *          $
 */
public class Application extends Composite implements BooksReadHandler, SourceCodes
{
    private static ApplicationUiBinder uiBinder = GWT.create(ApplicationUiBinder.class);

    interface ApplicationUiBinder extends UiBinder<Widget, Application>
    {
    }

    @UiField
    Hyperlink jsonToPojo;

    @UiField
    Hyperlink jsonToGxtModel;

    @UiField
    Hyperlink xmlToPojo;

    @UiField
    Hyperlink xmlToGxtModel;

    @UiField
    Label sourceCode;

    @UiField
    Label status;

    BooksClient client = null;


    public Application()
    {
        client = new BooksClient();
        initWidget(uiBinder.createAndBindUi(this));
        EventBus.get().addHandler(BooksReadEvent.getType(), this);
    }


    @UiHandler("jsonToPojo")
    void onFromJsonAsPojo(ClickEvent e)
    {
        client.readFromJson(Book.JSON, JSON_TO_POJO);
    }


    @UiHandler("jsonToGxtModel")
    void onFromJsonAsGxtModel(ClickEvent e)
    {
        client.readFromJson(BookModel.JSON, JSON_TO_GXT_MODEL);
    }


    @UiHandler("xmlToPojo")
    void onFromXmlAsPojo(ClickEvent e)
    {
        client.readFromXml(Book.XML, XML_TO_POJO);
    }


    @UiHandler("xmlToGxtModel")
    void onFromXmlAsGxtModel(ClickEvent e)
    {
        client.readFromXml(BookModel.XML, XML_TO_GXT_MODEL);
    }


    @Override
    public void onTimeInterval(BooksReadEvent event)
    {
        List<Object> books = event.getBooks();
        TimeInterval timeInterval = event.getTimeInterval();
        if (books != null)
        {
            sourceCode.setText(event.getSourceCode());
            Object book = books.get(0);
            String kind = book instanceof Book ? "POJO" : "GXT model";
            status.setText("Successfully read " + event.getBooks().size() + " books as " + kind + " in "
                    + timeInterval.ms() + " ms.");
        }
        else
        {
            sourceCode.setText("");
            status.setText("Error reading books");
        }
    }
}
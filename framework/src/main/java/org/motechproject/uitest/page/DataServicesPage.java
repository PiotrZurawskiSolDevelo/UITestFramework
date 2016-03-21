package org.motechproject.uitest.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * A class that represents data services page. Has methods which check functionality of
 * data browser, schema editor and data services settings.
 */

public class DataServicesPage extends AbstractBasePage {

    public static final By ENTITY_NAME_FIELD = By.name("inputEntityName");
    public static final By NEW_ENTITY_BUTTON = By.id("newEntityButton");
    public static final By SAVE_ENTITY_BUTTON = By.id("saveNewEntityButton");
    public static final By DATA_SERVICES_BUTTON = By.id("modulelink_data-services");
    public static final By SCHEMA_EDITOR_BUTTON = By.id("mdsTab_schemaEditor");
    public static final By BROWSE_INSTANCES_BUTTON = By.id("browseInstancesButton");
    public static final By ADD_NEW_INSTANCE_BUTTON = By.id("addNewInstanceButton");
    public static final By ENTITY_SPAN = By.id("select2-chosen-2");
    public static final By FIELD_TYPE_DROPDOWN = By.id("new-field-type");

    public static final String HOME_PATH = "/module/server/home#";

    public DataServicesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Method creates new entity in MDS schema editor.
     * @param entityName new entity name
     * @return method returns text that appears in schema editor entity input after creating new entity, should be the same as new entity name, should be checked in tests
     */
    public String createNewEntity(String entityName) throws InterruptedException {
        clickWhenVisible(DATA_SERVICES_BUTTON);
        clickWhenVisible(SCHEMA_EDITOR_BUTTON);
        clickWhenVisible(NEW_ENTITY_BUTTON);
        waitForElement(ENTITY_NAME_FIELD);
        setTextToFieldNoEnter(ENTITY_NAME_FIELD, entityName);
        clickWhenVisible(SAVE_ENTITY_BUTTON);
        waitForElement(BROWSE_INSTANCES_BUTTON);
        return getText(ENTITY_SPAN);
    }

    public void editEntity(String entityName) throws InterruptedException {
        clickWhenVisible(DATA_SERVICES_BUTTON);
        clickWhenVisible(SCHEMA_EDITOR_BUTTON);
        clickWhenVisible(By.xpath("//div[@id='s2id_selectEntity']/a"));
        clickWhenVisible(By.xpath(String.format("//div[@class='select2-result-label']/div/div/strong[text() = '%s']", entityName)));
    }

    public void addNewField(String fieldName) throws InterruptedException {
        setTextToFieldNoEnter(By.xpath("//div[@id='new-field-displayName']/input"), fieldName);
        setTextToFieldNoEnter(By.xpath("//div[@id='new-field-name']/input"), fieldName);
        clickWhenVisible(FIELD_TYPE_DROPDOWN);
        clickWhenVisible(By.xpath("//div[@class='select2-result-label']/div/div/strong[text() = 'Boolean']"));
        clickWhenVisible(By.xpath("//a[@ng-click='createField()']"));
        waitForElementToBeEnabled(By.xpath("//button[@ng-click='saveChanges()']"));
        clickWhenVisible(By.xpath("//button[@ng-click='saveChanges()']"));
        waitForElementToBeDisabled(By.xpath("//button[@ng-click='saveChanges()']"));
    }

    public void addNewLookup() throws InterruptedException {
        clickWhenVisible(By.xpath("//button[@ng-click='setAvailableFields()']"));
        clickWhenVisible(By.xpath("//button[@ng-click='addNewIndex()']"));
        clickWhenVisible(By.xpath("//div[@class='modal-footer']/button[text()='Close']"));
    }

    /**
     * Method that goes to data services page and enters entity table.
     * @param entityName name of entity table that we want to enter
     */
    public void goToEntityTable(String entityName) throws InterruptedException {
        clickWhenVisible(DATA_SERVICES_BUTTON);
        clickWhenVisible(By.id(String.format("entity_%s", entityName)));
        waitForElement(ADD_NEW_INSTANCE_BUTTON);
    }

    @Override
    public String expectedUrlPath() {
        return HOME_PATH;
    }

    @Override
    public void goToPage() {
        getDriver().get(getMotechUrl() + HOME_PATH);
    }
}

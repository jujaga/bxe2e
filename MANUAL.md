# Introduction #

The E2Everest project is a working proof-of-concept E2E-DTC 1.4 implementation that is compatible for use with the Physicians Data Collaborative network. The code here is  originally built for the OSCAR EMR, but the framework itself is designed to be as EMR agnostic as possible.

This Manual is aimed at developers, system engineers and integrators looking to add E2E-DTC support to their Electronic Medical Record system. Since the underlying MARC-HI Everest Framework models the complex Clinical Document Architecture used in E2E, using this framework reduces the amount of time and effort required in order to properly implement E2E.

Ultimately, the E2Everest framework is a great stepping stone for quickly implementing a compatible E2E document export. Using this framework can rapidly accelerate the user's understanding of the CDA model. In turn, users can then look to extend and implement other CDA based documents from this framework.

## Prequisites ##

Since this framework was written in Java, at the bare minimum, the user should be able to work with and understand Java code. As well, the user should be familiar with Object Oriented Programming concepts, as well as a working knowledge of their EMR's data model.

Many Java applications utilize the Java Persistence API and its related ecosystem in order to interface with a database such as MySQL. As a consequence of this, E2Everest users are expected to know how to use the JPA framework and have a working understanding of common JPA frameworks such as Spring and Hibernate.

Lastly, E2Everest is only effective if the user has at least a conceptual level of understanding of HL7v3 and CDA. Users should be somewhat familiar with the RIM (Reference Information Model),  R-MIM (Refined Message Information Model), ITS (Implementable Technology Specification), and HL7 Data Types. Knowledge of HL7 and CDA is crucial for understanding the E2E specification since E2E is a specific implementation of the CDA.

## Resources ##

There exists plenty of resources available online which will cover many of the prerequisite topics. However, there are a few resources that have proven themselves to be invaluable when working with E2Everest:

- MARC-HI Everest Forums [http://te.marc-hi.ca/forums/default.aspx?g=topics&f=2](http://te.marc-hi.ca/forums/default.aspx?g=topics&f=2)
- Everest JavaDocs [http://te.marc-hi.ca/library/en/jdoc/jev/index.html?overview-summary.html](http://te.marc-hi.ca/library/en/jdoc/jev/index.html?overview-summary.html)
- Advanced Everest Developer's Handbook [http://www.lulu.com/shop/justin-fyfe/advanced-everest-developers-handbook/hardcover/product-21300345.html](http://www.lulu.com/shop/justin-fyfe/advanced-everest-developers-handbook/hardcover/product-21300345.html)

The Everest Forums contain many threads from the Everest community. Many of the issues and concerns that you may encounter are likely to have been addressed at one point or another in these forums. The Everest JavaDocs are self-explanatory; they tell you how the Everest data models work. Lastly, the eBook could be a useful reference for understanding the nuances of Everest.

# Design #

The main goal for this framework is to make it quick and easy to create an E2E document. E2Everest leverages the Everest framework in order to enforce CDA standard compliance as well as making it as easy as possible to learn how to properly use the complex CDA framework. Ultimately, the E2Everest library should operate mostly "under the hood" in the sense that most of the complexity of generating an E2E document should be behind the scenes.

The key behind making E2Everest easy to use is that it should do two relatively straightforward things: create a Clinical Document for a given patient demographic, and generate the xml from the Clinical Document. For example:

	// Populate Clinical Document
	ClinicalDocument clinicalDocument = E2ECreator.createEmrConversionDocument(demographicNo);

	// Output Clinical Document as String
	String output = EverestUtils.generateDocumentToString(clinicalDocument, true);

The complexity of generating an E2E document is put away "under the hood" so that the entry point of creating E2E documents is clean and easy to understand. Once E2Everest is implemented in an EMR's ecosystem, it should not take any more than a couple of lines such as the above example in order to generate an E2E document. In this fashion, other developers can utilize E2Everest without having to deeply understand the behind-the-scenes work.

## Everest ##

The MARC-HI Everest framework is designed to facilitate the creation, transmission, and consumption of HL7v3 structures. It contains raw datatypes defined in HL7 as building blocks for constructing documents, as well as object structures which have a one to one mapping with the XML representation of an HL7v3 structured message.

The key data structure that needs to be understood for implementing and handling an E2E document is the ClinicalDocument object. This object is defined under R-MIM, and is the basis for what E2E is built on top of. As such, any patient data that needs to be put into an E2E document must be converted into a ClinicalDocument object.

The ClinicalDocument object contains many attributes and sub-objects such as Author, Custodian, RecordTarget, Informant, and etc. The way the ClinicalDocument object is structured and nested in Everest is a direct mapping to how those elements will be represented in the XML version of the ClinicalDocument. The key thing to understand from the ClinicalDocument is that it is split up into two main components - the header, and the body.

Header information includes information directly associated to the patient such as their name, age, gender and address. It also includes things such as the author of the document (usually the patient's main doctor), who/what maintains the patient record, and where the E2E document is intended to go. The body includes specific data pieces regarding the patient such as their medications, family history, labs, and allergies.

Every one of these sub-objects is represented either directly by a specific object model, or by a standard template object such as an Observation, Organizer, Encounter, Act, or SubstanceAdministration. This is the essence of a CDA document. These objects come together in order to encapsulate and represent data regarding the patient.

Since E2E is a form of CDA document, it makes sense to build an E2E document from the Everest framework since it constrains and enforces CDA specification compliance. When used properly, you are guaranteed the creation of a valid CDA document. As such, any developer who wishes to send proper HL7v3 documents can do so by utilizing Everest.

Of course, Everest alone is not enough to create an E2E document. Although Everest supports CDA, the CDA itself does not prescribe exactly what type of data needs to be sent. Instead, the best way to think of the relationship between E2E and CDA is that the CDA is a template recipe specifying how elements are related to each other. E2E on the other hand specifies the data that should be in the document. This is where E2Everest comes in.

## E2Everest ##

The EMR-to-EMR Data Transfer & Conversion standard lays out the type of data that is expected to populate an E2E document. It does this by tying together CDA and HL7v3 data types into section templates. These section templates explain how things such as medications, allergies and labs should be structured.

Unfortunately, any new developer will likely have a very difficult time understanding how to interpret and use the E2E specification due to the overload of terminology and lack of a working understanding of how CDA really works. E2Everest bridges this knowledge gap by implementing the section templates. By using E2Everest, you can rely on the built section templates to assist in dictating what should and should not be in an E2E document.

E2Everest also features a high degree of code coverage and testing. 99% of the codebase is covered by testing, and all of its expected behaviors. Since every modeled section template has tests for specifying how each element should behave under many circumstances, a developer using E2Everest can leverage the unit tests to ensure that their changes will continue to output valid E2E content.

### Code Organization ###

The E2Everest codebase is split up into 6 separate sub-packages: 

- constant
- director
- extension
- model
- populator
- util

Each one of these sub-packages has a specialized purpose in E2E handling. The constant sub-package handles all of the standard OIDs, title strings, codesystems and mappings that an E2E document will contain. By localizing all of the data into this package, changing a value should be as simple as changing what is assigned and it will propagate to the rest of the other packages.

The director's main purpose is to direct what is to be done. Since the Physicians Data Collaborative uses the EMR Conversion Document specification in E2E, we have an E2ECreator class which will facilitate the creation of the conversion document. However, the E2E specification contains other specifications such as episodic documents, structured referrals and unstructured documents. Should they be required, the director will facilitate the creation of these types of documents.

The extension package is exactly as it sounds. Its main job is to allow for extensions to the CDA specification. There are certain section templates such as Alerts which requires extra fields outside what is provided in standard CDA objects. By extending the CDA objects in Everest, you are able to add in the extra fields as required by the E2E specification.

The model package handles the mappings between an EMR data model and the E2E section template models. The majority of the heavy lifting in E2Everest resides in this package. This package has two main jobs: aggregating the EMR data model into a consumable format, and mapping specific elements from the EMR data model to its respective E2E section template.

The populator package handles the top-level requirements and assembly of the ClinicalDocument. While the model package maps EMR objects to CDA sub-objects, the populator assembles the CDA sub-objects together into the ClinicalDocument hierarchy. Populator also has the task of enforcing E2E level specification constraints by making sure that all the required elements in E2E are present as expected.

The util package contains frequently used utility functions which do not necessarily fit in any of the previous 5 packages. It contains validation classes which check for Everest/CDA conformance, as well as XSD validation. The rest of the utility functions reside in EverestUtils which contains functionality such as generating XML output from ClinicalDocument objects, formatting, common HL7 object creation functions, and caching lookup functions to reduce database load impact.

# Implementation #

When implementing E2Everest into a different EMR environment, there are a few key guidelines that should be followed:

- Make sure the EMR data model is grouped together and easy to access
- Go through each model mapping carefully and make sure you understand how each element should behave when passing in valid, invalid and garbage data
- Use the existing code as a model and guideline for modifications and extensions to the codebase

The reason you want the EMR data model to be grouped together is so that you can quickly identify what tables and elements are required in order to fully represent a patient in E2E. Say for example your EMR's medication section consists of two tables: medications and prescriptions. You would optimally want to cluster the two together so that you can pass them both into the respective MedicationsModel and perform your mapping. The clustering can be done many ways such as wrapping them together in a wrapper object, or by using database views to create a new consolidated view of the data - it depends on how your EMR is set up.

Going through every one of the mapping attributes in each model is critical because every one of the elements is required by the E2E specification in some form. When considering how the data should behave, you can use the original models and tests as a guide as to how they should behave. As with any other implementation mapping, you do want to refer back to the E2E Consolidated Implementation Guide to absolutely make sure you are satisfying the specification.

## Refactoring ##

When refactoring E2Everest to handle your specific EMR's data model, make sure test as many possible potential inputs as possible. Generally, you want to test for both the normal data case as well as the null/empty case as these two scenarios are the most common when dealing with inconsistent EMR data.

It is expected that most of your code modifications to make E2Everest work with your EMR will be in the model and util packages. You should expect to verify the populator in context of your own EMR, but you should not expect to see much code change in the populator other than making sure your EMR data model objects pass through to the models correctly.

Depending on how your EMR accesses the database, make sure to put any direct database access calls in a caching wrapper to improve performance. For example, if you expect to be referring to a code table frequently, you should attempt to cache the results in a hashmap or something similar. This will significantly reduce database overhead because for the most part, only a specific subset of the codes will be used, and they will be used repeatedly.

Any constants that may be needed should be placed in the constants package and referred from there. E2E contains many "boilerplate" OIDs and other constants that show up repeatedly. By making sure all the constants are well organized and in the same location, it significantly reduces the overhead of refactoring code.

Lastly, as you become more familiar with CDA and E2E, you will notice that many parts are replicated almost in entirety. Whenever you notice a section that is repeated elsewhere in the document, check to see if they are derived from the same template. If they are, make a strong effort to create a template for that section and maximize its re-usability. There exists many template models in the current E2Everest code that can be used as example models.

# Final Remarks #

Implementing E2E support from scratch is a huge undertaking and will require many developer hours in order to complete. This has been the case for multiple EMR vendors thusfar. However, the utilization of the Everest library has proven repeatedly that it can drastically reduce the amount of time required to properly support E2E.

Even with E2Everest however, there is no going around the large resource commitment needed to properly support E2E document export. However, what we hope to demonstrate is that this is now a well-traveled road for implementation, and that by following in our footsteps, you can get your own E2E document exports going in your own EMR much more rapidly.

It is without a doubt that most of the development time will be spent understanding the mappings between EMR and E2E data models. This is an inevitable part of the work involved. However, by leveraging E2Everest and Everest, you can expect to reduce the amount of time required to understand complex CDA nuances and focus more of your development time on actual implementation.

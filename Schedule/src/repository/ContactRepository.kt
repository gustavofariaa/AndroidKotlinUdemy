package repository

import entity.ContactEntity

class ContactRepository {

    companion object {
        private val contactList = mutableListOf<ContactEntity>()

        fun getList(): List<ContactEntity> = contactList

        fun save(contact: ContactEntity) = contactList.add(contact)

        fun delete(contact: ContactEntity) {
            var index = 0;
            for(item in contactList.withIndex()) {
                if (contact.name == item.value.name
                    && contact.phone == item.value.phone) {
                    index = item.index
                    break
                }
            }
            contactList.removeAt(index)
        }
    }

}
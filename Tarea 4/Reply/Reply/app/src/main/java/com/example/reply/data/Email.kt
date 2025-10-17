
package com.example.reply.data

import androidx.annotation.StringRes

data class Email(
    /** Unique ID of the email **/
    val id: Long,
    /** Sender of the email **/
    val sender: Account,
    /** Recipient(s) of the email **/
    val recipients: List<Account> = emptyList(),
    /** Title of the email **/
    @StringRes val subject: Int = -1,
    /** Content of the email **/
    @StringRes val body: Int = -1,
    /** Which mailbox it is in **/
    var mailbox: MailboxType = MailboxType.Inbox,

    var createdAt: Int = -1
)
